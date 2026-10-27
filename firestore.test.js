const {
  initializeTestEnvironment,
  assertFails,
  assertSucceeds,
} = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

let testEnv;
const PROJECT_ID = process.env.GCP_PROJECT || "demo-no-project";
const ALICE_UID = "alice_123";
const BOB_UID = "bob_456";

const [emulatorHost, emulatorPortStr] = (process.env.FIRESTORE_EMULATOR_HOST || "127.0.0.1:8085").split(":");
const emulatorPort = parseInt(emulatorPortStr, 10);

before(async () => {
  const rules = fs.readFileSync("./firestore.rules", "utf8");
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules,
      host: emulatorHost,
      port: emulatorPort,
    },
  });
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

beforeEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

function validUserProfile(uid) {
  const now = new Date();
  return {
    userId: uid,
    displayName: "Azizbek Karimov",
    email: "azizbek@example.com",
    cefrLevel: "A2",
    learningGoal: "Sayohat va ish uchun",
    dailyGoalMinutes: 15,
    placementCompleted: true,
    placementScore: 65,
    streakDays: 3,
    xpPoints: 240,
    completedLessonsCount: 4,
    wordsLearnedCount: 25,
    todayMinutesDone: 10,
    lastActivityDate: "2026-10-26",
    createdAt: now,
    updatedAt: now,
  };
}

test("Unauthenticated user: cannot read user profile", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).get());
});

test("Authenticated user: can create and read own profile", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).set(validUserProfile(ALICE_UID))
  );
  await assertSucceeds(aliceDb.collection("users").doc(ALICE_UID).get());
});

test("Authenticated user: cannot read or write another user's profile", async () => {
  await testEnv.withSecurityRulesDisabled(async (context) => {
    await context.firestore().collection("users").doc(ALICE_UID).set(validUserProfile(ALICE_UID));
  });

  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  await assertFails(bobDb.collection("users").doc(ALICE_UID).get());
  await assertFails(
    bobDb.collection("users").doc(ALICE_UID).set(validUserProfile(BOB_UID))
  );
});

test("Shadow update test: rejects ghost field on user profile", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const payloadWithGhost = {
    ...validUserProfile(ALICE_UID),
    isAdmin: true,
  };
  await assertFails(aliceDb.collection("users").doc(ALICE_UID).set(payloadWithGhost));
});

test("VocabProgress: owner can create, query with userId filter, and cross-user fails", async () => {
  const now = new Date();
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const vocabDoc = {
    userId: ALICE_UID,
    wordId: "w_hello",
    english: "Hello",
    uzbek: "Salom",
    learned: true,
    favorite: false,
    masteryBox: 2,
    nextReviewEpochDay: 20750,
    createdAt: now,
    updatedAt: now,
  };

  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("vocab_progress").doc("w_hello").set(vocabDoc)
  );

  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("vocab_progress").where("userId", "==", ALICE_UID).get()
  );

  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  await assertFails(
    bobDb.collection("users").doc(ALICE_UID).collection("vocab_progress").doc("w_hello").get()
  );
});

test("LessonProgress & QuizAttempts: owner can create and query with userId filter", async () => {
  const now = new Date();
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();

  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("lesson_progress").doc("gram_a1_tobe").set({
      userId: ALICE_UID,
      lessonId: "gram_a1_tobe",
      skillType: "GRAMMAR",
      title: "Verb To Be (am, is, are)",
      completed: true,
      bestScore: 90,
      attemptsCount: 1,
      createdAt: now,
      updatedAt: now,
    })
  );

  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("quiz_attempts").doc("att_001").set({
      userId: ALICE_UID,
      lessonId: "gram_a1_tobe",
      questionId: "q1",
      skillType: "GRAMMAR",
      submittedAnswer: "I am a student",
      isCorrect: true,
      score: 100,
      errorCategory: "NONE",
      feedbackUzbek: "Barakalla! 'I' olmoshidan keyin 'am' ishlatiladi.",
      createdAt: now,
      updatedAt: now,
    })
  );
});
