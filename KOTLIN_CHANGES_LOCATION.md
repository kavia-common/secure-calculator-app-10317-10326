# Kotlin login + calculator changes location

If you don't see the expected Kotlin **login + calculator** implementation in this workspace, it is because the changes were committed on a different git branch.

## Where the changes are

Repository: `secure-calculator-app-10317-10326`

Branch containing the changes:
- `cga-cg39623342`

That branch includes (among others):
- `kotlin_frontend/app/src/main/kotlin/org/example/app/LoginActivity.kt`
- `kotlin_frontend/app/src/main/kotlin/org/example/app/CalculatorActivity.kt`
- `kotlin_frontend/app/src/main/res/layout/activity_login.xml`
- `kotlin_frontend/app/src/main/res/layout/activity_calculator.xml`
- Updates to `AndroidManifest.xml`, `MainActivity.kt`, and other resources.

The default workspace branch may be:
- `cga-cmc77e72ad`

On that branch, the Kotlin app may still be the original template with `MainActivity` as launcher and without the login/calculator screens.

## How to switch to the branch

From the `secure-calculator-app-10317-10326` directory:

```bash
git fetch --all --prune
git checkout cga-cg39623342
```

Verify:

```bash
ls kotlin_frontend/app/src/main/kotlin/org/example/app/
```

You should see `LoginActivity.kt` and `CalculatorActivity.kt` on `cga-cg39623342`.
