# 2016102930-tbn-hackathon-team6
## 特殊生物資料調查

### 動機
- 提供地方資料的統計及過濾
- 提升可讀性
- 了解物種分布情形


### 相關套件
- Spark

	Apache Spark是一個開源叢集運算框架，最初是由加州大學柏克萊分校AMPLab所開發，使用了記憶體內運算技術，能在資料尚未寫入硬碟時即在記憶體內分析運算。

	<center> ![Spark]
	(./images/spark.png)

- ELK

	ELK stack 分散式日誌收集系統，主要由 **Elasticsearch**、 **Logstash**、 **Kibana** 所組成
	
	- Elasticsearch:提供資料儲存及檢索功能來過濾資料，並提供索引、搜索、排序和過濾等功能
	- Logstash:協助收集各種 log 或其他資訊，並根據所需結果過濾轉換成所需資料
	- Kibana:基於 elasticsearch 的前端視覺化工具，並根據 log 資料在瀏覽器上做長條圖、圓餅圖等...統計顯示
	
	<center> ![ELK](https://github.com/Max821214/2016102930-tbn-hackathon-team6/blob/master/images/elk.png)


### 架構流程

<center> ![Flow](./images/flow.png)

### 未來展望
- 目前只有針對台中地區資料分析，未來將會加入全國資料做統計分析
- 未來可以結合觀光教育，提升人民對於台灣特有物種的認識
