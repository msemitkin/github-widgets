build-Function:
	./gradlew --no-daemon clean nativeCompile
	echo '#!/bin/sh' > ./build/bootstrap
	echo 'set -euo pipefail' >> ./build/bootstrap
	echo './githubstreakwidget' >> ./build/bootstrap
	chmod +x ./build/bootstrap
	cp ./build/bootstrap $(ARTIFACTS_DIR)
	cp ./build/native/nativeCompile/githubstreakwidget $(ARTIFACTS_DIR)
