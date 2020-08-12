# java-helm-test

Service.java spins up a basic Http server with two endpoints (`/health` & `/read`). The `/read` endpoint list all yaml files in the `/cfg` that contain `.yaml`. HelmTest.java calls the `/read` endpoint to validate that the cfg files were mounted correctly. 

Test it out: 
```
helm upgrade java-helm-test . --install --wait --debug --namespace default --set-file helmTest=HelmTest.java
helm test java-helm-test
```

