/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.sample.test.presenter;

import android.content.Context;
import android.test.AndroidTestCase;

import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.interactor.GetUserList;
import com.fernandocejas.android10.sample.presentation.mapper.UserModelDataMapper;
import com.fernandocejas.android10.sample.presentation.presenter.UserListPresenter;
import com.fernandocejas.android10.sample.presentation.view.UserListView;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.observers.TestSubscriber;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class UserListPresenterTest extends AndroidTestCase {

    private UserListPresenter userListPresenter;

    @Mock
    private Context mockContext;
    @Mock
    private UserListView mockUserListView;
    @Mock
    private GetUserList mockGetUserList;
    @Mock
    private UserModelDataMapper mockUserModelDataMapper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        userListPresenter = new UserListPresenter(mockGetUserList, mockUserModelDataMapper);
        userListPresenter.setView(mockUserListView);
    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        RxJavaPlugins.getInstance().registerSchedulersHook(null);
    }

    public void testUserListPresenterInitialize() {
        given(mockUserListView.context()).willReturn(mockContext);
        given(mockGetUserList.execute()).willReturn(Observable.<List<User>>empty());
        userListPresenter.initialize();

        verify(mockUserListView).hideRetry();
        verify(mockUserListView).showLoading();
        verify(mockGetUserList).execute();
    }
}
