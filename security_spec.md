# Security Specification — UzEnglish AI

## 1. Data Invariants
1. Every document in `/users/{userId}` and its subcollections (`vocab_progress`, `lesson_progress`, `quiz_attempts`) MUST only be readable and writable by the authenticated user whose `request.auth.uid == userId` AND whose document `userId` field equals `request.auth.uid`.
2. All string fields have strict `.size()` upper bounds to prevent Denial-of-Wallet payload bloat.
3. All document ID path variables (`userId`, `wordId`, `lessonId`, `attemptId`) are validated with `isValidId()` on single-document operations (`get`, `create`, `update`, `delete`).
4. Immutable fields (`userId`, `createdAt`, `wordId`, `lessonId`) cannot be modified during `update`.
5. Every `create` and `update` enforces `FieldValue.serverTimestamp()` (`is timestamp && <= request.time`) and strict field allowlists via `keys().hasOnly(...)` and `diff(existing()).affectedKeys().hasOnly(...)`.

## 2. The Dirty Dozen Payloads
1. Unauthenticated read on `/users/alice_123` -> REJECTED
2. Unauthenticated list on `/users/alice_123/vocab_progress` -> REJECTED
3. Cross-user read (`bob_456` reading `/users/alice_123`) -> REJECTED
4. Cross-user write (`bob_456` updating `/users/alice_123`) -> REJECTED
5. Identity spoofing (`alice_123` creating `/users/alice_123/vocab_progress/w1` with `userId: "bob_456"`) -> REJECTED
6. Ghost field injection (`isVerified: true` on `/users/alice_123`) -> REJECTED
7. Immutable field mutation (changing `createdAt` or `userId` on update) -> REJECTED
8. Type poisoning (`xpPoints: "lots"` instead of `int`) -> REJECTED
9. Boundary overflow (`displayName` > 100 chars or `dailyGoalMinutes` > 180) -> REJECTED
10. Invalid CEFR enum (`cefrLevel: "Z9"`) -> REJECTED
11. Unfiltered list query without `where("userId", "==", uid)` -> REJECTED
12. Future timestamp spoofing (`createdAt` > `request.time`) -> REJECTED
