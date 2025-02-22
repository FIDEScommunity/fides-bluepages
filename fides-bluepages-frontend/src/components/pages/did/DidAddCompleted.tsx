import * as React from 'react';
import { FC } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { BackLink, MaxContentWidthContainer, OButton, OCard } from '../../molecules';
import { AddCheckmark } from '../../atoms';

export const DidAddCompleted: FC = () => {
    const navigate = useNavigate();
    const {t} = useTranslation();

    return (
        <MaxContentWidthContainer className="mt-2">
            <div>
                <BackLink label="Back" onClick={() => navigate('/did')} className="mb-4"/>
            </div>
            <OCard className="p-3 md:p-3 lg:p-6">
                <div className="flex align-items-center mb-4">
                    <AddCheckmark width={'5rem'} height={'5rem'}/>
                    <div className="ml-4 flex flex-column">
                        <div className="text-xl font-bold mb-1">{t('screens.addDidCompleted.title')}</div>
                        <div className="">{t('screens.addDidCompleted.subTitle')}</div>
                    </div>
                </div>
                <OButton label={t('screens.addDidCompleted.addButtonLabel')}
                         className="mt-2 mr-2"
                         onClick={() => navigate('/did/add')}/>
                <OButton label={t('screens.addDidCompleted.returnToOverview')}
                         className="mt-2"
                         severity={'secondary'}
                         onClick={() => navigate('/did')}/>
            </OCard>


        </MaxContentWidthContainer>
    );
}

