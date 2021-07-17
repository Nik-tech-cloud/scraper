package result.scraper;

import org.jsoup.Jsoup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.jsoup.nodes.*; //Document za html file


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;


@SpringBootApplication
@RestController
@Configuration
@EnableAsync
public class ScraperApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScraperApplication.class, args);

	}

	@RequestMapping("/data/{num}")
	public String siteUpload(@RequestParam("num") int socasniKlici){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(socasniKlici);
		executor.setMaxPoolSize(socasniKlici);
		executor.setQueueCapacity(100);
		executor.setThreadNamePrefix("userThread-");
		executor.initialize();

		Map<String, String> map = new HashMap<>();
		map.put("result1",scrap("https://www.result.si/projekti/"));
		map.put("result2",scrap("https://www.result.si/o-nas/"));
		map.put("result3",scrap("https://www.result.si/kariera/"));
		map.put("result4",scrap("https://www.result.si/blog/"));
		return map.toString();

	}

	public static String scrap (String url) {
		try {
			Document doc = Jsoup.connect(url).get();
			return doc.select("h2.et_pb_module_header").text();
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}

}
