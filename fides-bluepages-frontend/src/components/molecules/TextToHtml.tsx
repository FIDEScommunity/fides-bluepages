import React, { useMemo } from 'react';
import * as DOMPurify from 'dompurify';
import './TextToHtml.css'

export interface TextToHtmlProps {
    value?: string | undefined;
}

export const TextToHtml: React.FC<TextToHtmlProps> = (props) => {


    const htmlSanitized = useMemo(() => {
        if (props.value === undefined) {
            return '';
        }
        return DOMPurify.sanitize(props.value!);
    }, [props.value]);

    if (props.value === undefined) {
        return null;
    }

    return (
        <span className="text-to-html"
              dangerouslySetInnerHTML={{
                  __html: htmlSanitized,
              }}
        ></span>
    )
};

