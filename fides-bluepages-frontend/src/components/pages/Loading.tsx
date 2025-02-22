import { FC } from 'react';
import styled from 'styled-components';

interface LoadingProps {
    message?: string;
}

export const Loading: FC<LoadingProps> = (props) => {
    return (
        <Root className="justify-content-center align-items-center">
            <div>{props.message}</div>
            <div>Loading...</div>
        </Root>
    )
}

const Root = styled.div`
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, .1);
    display: flex;
    flex-direction: row;
    margin: 0 auto;
    min-height: 100vh;
    overflow: hidden;
    position: relative;
    width: 100%;
`;

