import { Outlet } from 'react-router-dom';

import React from 'react';
import { AboutMenu, Header } from '../organisms';
import styled from 'styled-components';
import { ToastContainer } from '../molecules/ToastContainer';

export const AboutMenuLayout = () => {
    return (
        <Root>
            <ContentContainer>
                <Header/>
                <ToastContainer/>
                <BodyContainer className="flex grid-nogutter ml-4 md:ml-2 m-4">
                    <AboutMenu className="hidden md:block pl-0 ml-0 md:pr-2 col-0 md:col-2"/>
                    <div className="col-12 md:col-10">
                        <Outlet/>
                    </div>
                </BodyContainer>
            </ContentContainer>
        </Root>
    );
};


const Root = styled.div`
    margin: 0 auto;
    min-height: 100vh;
    width: 100%;
`;
const BodyContainer = styled.div`
    //padding-top: 20px;
    //padding-right: 20px;
    //padding-left: 20px;
`;
const ContentContainer = styled.div`
    width: 100%;
`;
