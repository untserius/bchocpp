package com.axxera.ocpp.webScoket.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.message.ConfigurationKey;
import com.axxera.ocpp.message.FinalData;
import com.axxera.ocpp.model.ocpp.ChargerDefaultConfiguration;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.service.StatusDefaultConfigService;


@Service
public class StatusDefaultConfigSeviceImpl implements StatusDefaultConfigService{

	@Autowired
	private Utils Utils;

	@Override
	public void getCompareDataDcc(FinalData data, List<ChargerDefaultConfiguration> getdatafromCDC, WebSocketSession session, String stationRefNum) {
		List<ConfigurationKey> configurationKey = data.getConfigurationKey();
		configurationKey.forEach(ConfigurationKey->{
			String configKey = ConfigurationKey.getKey();
			String configValue = ConfigurationKey.getValue();
			
			getdatafromCDC.forEach(ChargerDefaultConfiguration->{
				String defaultConfigKey = ChargerDefaultConfiguration.getKeys();
				String defaultConfigValue = ChargerDefaultConfiguration.getValue();
				
				if(configKey.equalsIgnoreCase(defaultConfigKey)) {
					if(configValue.equalsIgnoreCase(defaultConfigValue)) {
						
					}else {
						try {
							Thread.sleep(1000);
							String changeConfiguration ="[2,\""+Utils.getRandomNumber("")+":CNF\",\"ChangeConfiguration\",{\"key\":"+configKey+",\"value\":\""+defaultConfigValue+"\"}]";
							Utils.chargerMessage(session, changeConfiguration, stationRefNum);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				}
			});
		});
	
	}

}
