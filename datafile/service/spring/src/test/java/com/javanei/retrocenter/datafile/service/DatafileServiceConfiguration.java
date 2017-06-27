package com.javanei.retrocenter.datafile.service;

import com.javanei.retrocenter.datafile.Datafile;
import com.javanei.retrocenter.datafile.entity.DatafileEntity;
import com.javanei.retrocenter.datafile.persistence.DatafileDAO;
import com.javanei.retrocenter.datafile.persistence.GameDAO;
import com.javanei.retrocenter.datafile.persistence.GameFileDAO;
import com.javanei.retrocenter.datafile.persistence.ReleaseDAO;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatafileServiceConfiguration {

    public static Datafile datafile = new Datafile("name 01", "no-intro", "1.00",
            "description 01", "author 01", "2017", "teste@teste.com",
            "homepage 01", "http://www.teste.com", "comment 01");
    ;

    @Bean
    public DatafileService datafileService() {
        return new DatafileService();
    }

    @Bean
    public DatafileDAO datafileDAO() {
        DatafileDAO datafileDAO = Mockito.mock(DatafileDAO.class);
        //Mockito.when(datafileDAO.saveAndFlush(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(datafileDAO.saveAndFlush(Mockito.any())).thenAnswer(new Answer<DatafileEntity>() {
            @Override
            public DatafileEntity answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                DatafileEntity r = (DatafileEntity) args[0];
                r.setId(1l);
                return r;
            }
        });
        return datafileDAO;
    }

    @Bean
    public GameDAO gameDAO() {
        GameDAO gameDAO = Mockito.mock(GameDAO.class);
        Mockito.when(gameDAO.saveAndFlush(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());
        return gameDAO;
    }

    @Bean
    public GameFileDAO gameFileDAO() {
        GameFileDAO gameFileDAO = Mockito.mock(GameFileDAO.class);
        Mockito.when(gameFileDAO.saveAndFlush(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());
        return gameFileDAO;
    }

    @Bean
    public ReleaseDAO releaseDAO() {
        ReleaseDAO releaseDAO = Mockito.mock(ReleaseDAO.class);
        Mockito.when(releaseDAO.saveAndFlush(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());
        return releaseDAO;
    }
}