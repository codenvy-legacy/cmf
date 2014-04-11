grammar MetaModel;

metaModel           :   ( dynamicConstraint | staticConstraint )+ EOF;

dynamicConstraint   :   'some dynamic constraint stub';

staticConstraint    :   'StaticConstraints{'
                                           (elementStructure | elementRelation)+
                        '}'
                    ;

elementStructure    :   'Element{'
                                            elementType
                                            property*
                                            innerElement*
                        '}'
                    ;

elementRelation     :   'Relation{'
                                            alignment
                                            direction
                                            relation
                        '}'
                    ;

property            :   'Property{'
                                            propertyName
                                            propertyType
                                            defaultValue?
                                            visibility
                        '}'
                    ;

elementType         :   'type'        ':'   TEXT;
innerElement        :   'element'     ':'   TEXT;
alignment           :   'alignment'   ':'   ALIGNMENT;
direction           :   'direction'   ':'   DIRECTION;
relation            :   'relation'    ':'   TEXT '-' TEXT;
propertyName        :   'name'        ':'   TEXT;
propertyType        :   'type'        ':'   PROPERTY_TYPE;
defaultValue        :   'value'       ':'   TEXT;
visibility          :   'visibility'  ':'   VISIBILITY;

PROPERTY_TYPE       :   'BOOLEAN'   |
                        'INTEGER'   |
                        'FLOAT'     |
                        'STRING'    |
                        'boolean'   |
                        'integer'   |
                        'float'     |
                        'string'
                    ;
VISIBILITY          :   'HEADER'    |
                        'BODY'      |
                        'HIDDEN'    |
                        'header'    |
                        'body'      |
                        'hidden'
                    ;
DIRECTION           :   'undirected'|
                        'directed'  |
                        'UNDIRECTED'|
                        'DIRECTED'
                    ;
ALIGNMENT           :   'vertical'  |
                        'horizontal'|
                        'VERTICAL'  |
                        'HORIZONTAL'
                    ;

TEXT                :   ([A-Za-z_] | [0-9])+;

WS                  :   [ \t\r\n] -> skip;
