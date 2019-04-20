
package com.kubernetes;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.Exec;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodBuilder;
import io.kubernetes.client.util.Config;

public class CreatePod {

	int threadCount;
	//running minikube on the proxy 8090 server  kubectl proxy --port=8090
	final static String K8_MASTER = "k8s://http://localhost:8090";
	final static String K8_NAMESPACE = "default";
	//location where configuration file is stored
	final static String K8_CONFIG = "C:\\Users\\Ayush\\.kube\\config";

	public static void main(String[] args) {
		try {
			//ApiClient client = Config.defaultClient();
			
			//getting the configutation from a config file
			ApiClient client = Config.fromConfig(K8_CONFIG);
			System.out.println("1.5");
			// Where to pickup master location from - DB ??
			client.setBasePath(K8_MASTER.substring(6)); // remove
			//client.set// k8s:/

			Configuration.setDefaultApiClient(client);
			CoreV1Api api = new CoreV1Api();
			// creating new pod
			V1Pod pod = new V1PodBuilder().withNewMetadata().withName("apod").endMetadata().withNewSpec()
					.addNewContainer().withName("testing-k8java").withImage("nginx:1.7.9")
					.endContainer().endSpec().build();
			// creating new pod in the default direct runner namespace specified
			api.createNamespacedPod(K8_NAMESPACE, pod, null);

			System.out.println("Successfully executed");

		} catch (ApiException e) {
			System.out.println(e.getResponseBody());
			e.printStackTrace();

		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
