package asuHelloWorldJavaFX;

public class HistoricalData {
	public String historicalDataName;
	public String historicalDataContent;
	public HistoricalData(String historicalDataName, String historicalDataContent) {
		super();
		this.historicalDataName = historicalDataName;
		this.historicalDataContent = historicalDataContent;
	}
	public String getHistoricalDataName() {
		return historicalDataName;
	}
	public void setHistoricalDataName(String historicalDataName) {
		this.historicalDataName = historicalDataName;
	}
	public String getHistoricalDataContent() {
		return historicalDataContent;
	}
	public void setHistoricalDataContent(String historicalDataContent) {
		this.historicalDataContent = historicalDataContent;
	}
	

}
