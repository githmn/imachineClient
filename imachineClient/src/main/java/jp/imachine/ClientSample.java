package jp.imachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.MultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

public class ClientSample {

	/**
	 * imachineOCR クライアントサンプルメイン関数
	 * @param args [0] : 処理する画像ファイルパス
	 */
	public static void main(String args[]) throws Exception{

		if(args.length != 1)
			throw new IllegalArgumentException("args[0] : 処理する画像ファイルパス");

		Client c = Client.create();
		File srcFile = new File(args[0]);

		if( ! srcFile.exists())
			throw new FileNotFoundException("指定されたファイルが見つからない");

		WebResource wr = c.resource(
				"http://imachineenv-3xjpvcepjk.elasticbeanstalk.com/ocr"
				);


		MultiPart multiPart = new MultiPart();
		// ファイル
		FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("img", srcFile);
		multiPart.bodyPart(fileDataBodyPart);

		// その他のフォーム地
		multiPart.bodyPart(new FormDataBodyPart("apiKey", "TEbGHTG6oJ4BoW7lOuMQul1IfUJwLEXz"));
		multiPart.bodyPart(new FormDataBodyPart("num", "4"));
		multiPart.bodyPart(new FormDataBodyPart("score", "true"));

		ClientResponse response = wr
				.header("Content-type", MediaType.MULTIPART_FORM_DATA)
				.header("Accept", MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, multiPart)
				;

		String json = response.getEntity(String.class);

		System.out.println(json);

	}

}
