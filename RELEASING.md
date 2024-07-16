# Release Playbook

## Cut a release branch

First you need to cut a release branch corresponding to the SDK and version you want to release.
For example, to cut a release branch for version `1.0.0` of the `wallet-sdk` module, you would do
the following from the `develop` branch:

```bash
git checkout -b release/wallet-sdk-v1.0.0
git push -u origin release/wallet-sdk-v1.0.0
```

Alternatively, you can create the new branch from the github UI.

## Let the CI build the release branch

CI will automatically bump the version of the module you're releasing and kick off tests. Note: When
bumping a module that's dependent on `core`, the `bumpVersion` task will also check if the `core`
version needs to be bumped first, and if so, will fail and prompt you to release `core` first.

If things succeed, it will create a PR to merge the release branch into `main` and assign you as the
reviewer. When the branch is ready to be merged, you can merge it and delete the release branch.

## Merge release and publish draft release

When you merge the release branch into `main`, it will trigger a github action that will add a tag
and draft release for you.

Add appropriate release notes to the draft release and publish it. When the release is published,
a github action will be triggered that will publish the artifacts to maven central, as well as
publish the documentation and upload it to s3. You can check the status of the actions to ensure
they complete successfully.