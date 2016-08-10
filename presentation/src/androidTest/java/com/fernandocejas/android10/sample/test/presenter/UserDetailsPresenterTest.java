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
import com.fernandocejas.android10.sample.domain.interactor.GetUserDetails;
import com.fernandocejas.android10.sample.presentation.mapper.UserModelDataMapper;
import com.fernandocejas.android10.sample.presentation.presenter.UserDetailsPresenter;
import com.fernandocejas.android10.sample.presentation.view.UserDetailsView;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class UserDetailsPresenterTest extends AndroidTestCase {

    private static final int FAKE_USER_ID = 123;

    private UserDetailsPresenter userDetailsPresenter;

    @Mock
    private Context mockContext;
    @Mock
    private UserDetailsView mockUserDetailsView;
    @Mock
    private GetUserDetails mockGetUserDetails;
    @Mock
    private UserModelDataMapper mockUserModelDataMapper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        userDetailsPresenter = new UserDetailsPresenter(mockGetUserDetails,
                mockUserModelDataMapper);
        userDetailsPresenter.setView(mockUserDetailsView);
    }

    public void testUserDetailsPresenterInitialize() {
        given(mockUserDetailsView.context()).willReturn(mockContext);
        given(mockGetUserDetails.execute()).willReturn(Observable.<User>empty());
        userDetailsPresenter.initialize();
        verify(mockUserDetailsView).hideRetry();
        verify(mockUserDetailsView).showLoading();
        verify(mockGetUserDetails).execute().subscribe(any(Subscriber.class));
    }
}
