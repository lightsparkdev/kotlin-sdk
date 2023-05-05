LAST_CORE=$(git describe --tags --match "core-v*" --abbrev=0 --first-parent)
DIFF_FILES=$(git diff --name-only "$LAST_CORE" -- core/)
if [ "$DIFF_FILES" = "kotlin-sdk/core/gradle.properties" ] || [ -z "$DIFF_FILES" ]; then
  echo "No changes in core since $LAST_CORE"
  exit 0
else
  echo "Changes in core since $LAST_CORE" >&2
  echo "$DIFF_FILES" >&2
  exit 1
fi
