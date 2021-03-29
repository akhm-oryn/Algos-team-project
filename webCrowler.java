import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class webCrowler {
    public static void main(String[] args) {
        String root = "https://www.google.com";
        webCrowler bfs = new webCrowler();
        bfs.discoverWeb(root);

    }
    private Queue<String> queue;
    private List<String> discoveredWebsiteList;

    public webCrowler() {
        queue = new LinkedList<>();
        this.discoveredWebsiteList = new ArrayList<>();
    }
    public void discoverWeb(String root) { // we get the string to deal with URL
        this.queue.add(root);
        this.discoveredWebsiteList.add(root);
        
        while (!queue.isEmpty()) {
            String v = this.queue.remove(); // remove and show at first root, then same with other urls
            String rawHtml = readURL(v); //  read at first root, then the same with other urls

            String regexp = "https://(\\w+\\.)*(\\w+)"; // create reg expression for standard
            Pattern pattern = Pattern.compile(regexp); // there we bind standard for pattern to search
            Matcher matcher = pattern.matcher(rawHtml); // by pattern we seach urls

            while (matcher.find()) { // when we find url
                String w = matcher.group(); // we group them

                if(!discoveredWebsiteList.contains(w)) { // if does not contain urls in list
                    discoveredWebsiteList.add(w); // then we add them in list
                    System.out.println(": "+w); // output on console
                    queue.add(w); // add them in queue and continue until queue will be empty
                }
            }
        }
    }

    private String readURL(String v) { // get (read) the url
        String rawHtml = ""; // originally there free field

        try {
            URL url = new URL(v); // create object url to get string as url
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));// buffering

            String inputLine = ""; //  create field to input urls
            while ((inputLine = in.readLine()) != null) { // if it is empty
                rawHtml += inputLine;// then add them to show
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace(); // to show where caused problem
        }
        return rawHtml; // return url
    }

}
