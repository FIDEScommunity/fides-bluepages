import React, { FC } from 'react';
import { Trans, useTranslation } from 'react-i18next';
import { Image } from 'primereact/image';

export const UseCaseEInvoicing: FC = () => {
    const {t} = useTranslation();

    return (
        <div>
            <div className="flex flex-column">
                <h1 className="font-bold text-xl mt-3 mb-1">{t('screens.eInvoice.title')}</h1>
                <h2 className="font-bold text-l mt-2 mb-1">{t('screens.eInvoice.overview.title')}</h2>
                <p>{t('screens.eInvoice.overview.description')}</p>

                <h2 className="font-bold text-l mt-2 mb-1">{t('screens.eInvoice.scenarioDescription.title')}</h2>
                <p>{t('screens.eInvoice.scenarioDescription.description')}</p>

                <h2 className="font-bold text-l mt-2 mb-1">{t('screens.eInvoice.processFlowDiagram.title')}</h2>
                <p>{t('screens.eInvoice.processFlowDiagram.description')}</p>
                <Image src="/discovery_delivery.png" width="100%"/>

                <h2 className="font-bold text-l mt-2 mb-1">{t('screens.eInvoice.detailedProcessFlow.title')}</h2>
                <ol>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step1'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step2'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step3'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step4'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step5'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step6'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step7'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step8'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step9'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step10'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step11'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step12'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step13'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step14'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step15'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step16'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step17'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step18'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step19'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step20'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step21'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step22'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step23'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step24'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step25'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step26'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step27'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step28'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.detailedProcessFlow.step29'></Trans></li>
                </ol>

                <h2 className="font-bold text-l mt-2 mb-1">{t('screens.eInvoice.benefits.title')}</h2>
                <ul>
                    <li><Trans i18nKey='screens.eInvoice.benefits.benefit1'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.benefits.benefit2'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.benefits.benefit3'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.benefits.benefit4'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.benefits.benefit5'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.benefits.benefit6'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.benefits.benefit7'></Trans></li>
                </ul>

                <h2 className="font-bold text-l mt-2 mb-1">{t('screens.eInvoice.technicalRequirements.title')}</h2>
                <p>{t('screens.eInvoice.technicalRequirements.description')}</p>
                <ol>
                    <li><Trans i18nKey='screens.eInvoice.technicalRequirements.requirement1'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.technicalRequirements.requirement2'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.technicalRequirements.requirement3'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.technicalRequirements.requirement4'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.technicalRequirements.requirement5'></Trans></li>
                    <li><Trans i18nKey='screens.eInvoice.technicalRequirements.requirement6'></Trans></li>
                </ol>

                <h2 className="font-bold text-l mt-2 mb-1">{t('screens.eInvoice.conclusion.title')}</h2>
                <p>{t('screens.eInvoice.conclusion.description')}</p>
            </div>
        </div>
    );
};
