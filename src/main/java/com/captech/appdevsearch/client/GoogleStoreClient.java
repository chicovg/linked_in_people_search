package com.captech.appdevsearch.client;

import com.captech.appdevsearch.model.AndroidAppDetail;
import com.captech.appdevsearch.model.Profile;
import com.gc.android.market.api.MarketSession;
import com.gc.android.market.api.model.Market;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by victorguthrie on 3/6/15.
 */
@Component
public class GoogleStoreClient {

    private static final Logger logger = LoggerFactory.getLogger(GoogleStoreClient.class);

    private MarketSession session;

    public Profile searchAppsByDeveloper(final Profile profile) {
        try{
            final String query = "pub:" + profile.getFirstName() + " " + profile.getLastName()+"";
            Market.AppsRequest appsRequest = Market.AppsRequest.newBuilder()
                    .setQuery(query)
                    .setStartIndex(0).setEntriesCount(10)
                    .setWithExtendedInfo(true)
                    .build();

            getSession().append(appsRequest, new MarketSession.Callback<Market.AppsResponse>() {
                @Override
                public void onResult(Market.ResponseContext context, Market.AppsResponse response) {
                    if(null!=response){
                        logger.info("Android apps: " + response.getAppCount());
                        profile.setAndroidApps(convertAppResults(response.getAppList()));
                    }
                }
            });
            getSession().flush();
        } catch (Exception e){
            logger.error("Exception thrown while searching the Google Play Store", e);
        }
        return profile;
    }

    private MarketSession getSession(){
        if(null==session){
            session = new MarketSession();
            session.login("guthrievictor", "Q6f-Hgc-8MQ-AvC");
            session.getContext().setAndroidId(String.valueOf(nextLong()));
        }
        return session;
    }

    public static long nextLong() {
        long bits, val;
        do {
            bits = (new Random().nextLong() << 1) >>> 1;
            val = bits % 10000000000000L;
        } while (bits - val + (10000000000000L - 1) < 0L);
        return val + 30000000000000L;
    }

    private List<AndroidAppDetail> convertAppResults(List<Market.App> appList){
        List<AndroidAppDetail> appDetailList = new LinkedList<>();
        if(null!=appList){
            for(Market.App app : appList){
                appDetailList.add(appDetailFromMarketApp(app));
            }
        }
        return appDetailList;
    }

    private AndroidAppDetail appDetailFromMarketApp(Market.App app){
        return new AndroidAppDetail(app.getId(),
                app.getTitle(), app.getAppType().toString(), app.getCreator(),
                app.getVersion().toString(), app.getPrice().toString(), app.getRating(),
                app.getRatingsCount(), app.getCreatorId(), app.getPackageName(),
                app.getVersionCode(), app.getPriceCurrency(), app.getPriceMicros());
    }
}
