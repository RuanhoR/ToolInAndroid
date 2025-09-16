package cn.mcnb1123;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.content.*;
import android.*;
import android.content.pm.*;
import java.io.*;
import java.util.*;
import android.graphics.*;
import android.util.*;
public class MainActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    Button button = findViewById(R.id.a);
    button.setOnClickListener(new View.OnClickListener() {
      private long old_time = System.currentTimeMillis();
	  private long count = 0;
	  private Random rand = new Random();
	  private String dath = "/storage/emulated/0/Android/data/cn.mcnb1123/files/data";
      {
        try {
          String content = readTextFile(dath).trim();
          count = Integer.parseInt(content);
        } catch (Exception e) {
          count = 0;
        }
      }
      @Override
      public void onClick(View v) {
        try {
		  long time = System.currentTimeMillis() - old_time;
		  old_time = System.currentTimeMillis();
          if (time <= 60) throw new RuntimeException("点击过快，已拦截");
          count++;
          ((Button)v).setText(String.format("与上次间隔：%dms， 点击次数：%d次",time,count));
          String displayText = "";
          ((Button)v).setBackgroundColor(Color.rgb(
            rand.nextInt(256),
            rand.nextInt(256),
            rand.nextInt(256)
          ));
          if (count % 1919 == 0) {
            displayText = "你在玩什么奇怪的Play？";
          } else if (count % 114514 == 0) {
            displayText = "臭いです！\n好臭啊啊啊啊，手机不能要了";
          } else if (count == 100000) {
            displayText = "你是真的闲……";
          } else if (count % 1000 == 0) {
            displayText = "你已经点了" + count + "下！疯了吧？";
          } else if (count % 20 == 0 && count <= 300) {
            displayText = "加油";
          }
          if (!displayText.equals("")) {
            showText(displayText, 1);
          }	
          try {
            if (writeFile("data", String.valueOf(count), "1")) {}
          } catch (Exception e) {
            e.printStackTrace();
          }
        } catch (Exception e) {
			showText(e.getMessage(),1);
			Log.e("ClickError","Error",e);
		}
      }
    });
  }
  public void showText(String text, int time) {
    Context content = con();
    Toast.makeText(content, text, time).show();
  }
  public Context con() {
	  return MainActivity.this;
  }
  public boolean writeFile(String filePath, String content, String module) {
    File path = new File("");
    
    if (module == "0") {
      path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    } else if (module == "1") {
      path = getExternalFilesDir(null);
    } else if (module == "2") {
      path = getFilesDir();
    }
    File file = new File(path, filePath);
    try {
      try (FileWriter writer = new FileWriter(file)) {
        writer.write(content);
      }
      return true;
    } catch (IOException e) {
      return false;
    }
  }
  public String readTextFile(String filePath) throws IOException {
    StringBuilder text = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
      String line;
      while ((line = br.readLine()) != null) {
        text.append(line).append('\n');
      }
    }
    return text.toString();
  }
  public boolean isAssetFile(String filePath) {
	  Context content = con();
	  try {
		try (InputStream is = content.getAssets().open(filePath)) {
		  return true;
		}
	  } catch (IOException e) {
	   return false;
	}
  }
}
