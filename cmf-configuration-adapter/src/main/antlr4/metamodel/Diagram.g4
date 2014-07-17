/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

grammar Diagram;

import Common;

diagram                 :
                            rootElement
                            elements
                            connections?
                            propertyTypes?
                        ;

rootElement             :
                            elementName
                            BEGIN
                                rootElementBody
                            END
                        ;

elements                :
                            'Elements'
                            BEGIN
                                element+
                            END
                        ;

connections             :
                            'Connections'
                            BEGIN
                                connection+
                            END
                        ;

element                 :
                            elementName
                            BEGIN
                                elementBody
                            END
                        ;

connection              :
                            connectionName
                            BEGIN
                                connectionBody
                            END
                        ;

rootElementBody         :
                            elementRelation
                            elementComponents
                            elementProperties?

                        ;

connectionBody          :
                            connectionType
                            connectionPairs
                        ;

connectionName          :
                            TEXT
                        ;

connectionPairs         :
                            'Pairs'
                            BEGIN
                                connectionPair+
                            END
                        ;

connectionType          :   'Type'
                            BEGIN
                                CONNECTION_TYPE
                            END
                        ;

connectionPair          :
                            BEGIN
                                connectionStart '<>' connectionFinish
                            END
                        ;

connectionStart         :
                            TEXT
                        ;

connectionFinish        :
                            TEXT
                        ;

elementBody             :
                            elementRelation?
                            elementProperties?
                            elementComponents?
                        ;


elementComponents       :
                            'Components'
                            BEGIN
                                elementComponent+
                            END
                        ;

elementComponent        :
                            BEGIN
                                TEXT
                            END
                        ;

elementName             :
                            TEXT
                        ;

elementProperties       :
                            'Properties'
                            BEGIN
                                elementProperty+
                            END
                        ;

elementProperty         :
                            propertyName
                            BEGIN
                                propertyBody
                            END
                        ;

propertyBody            :
                            propertyType
                            propertyValue?
                        ;

propertyName            :
                            TEXT
                        ;

propertyType            :
                            'Type'
                            BEGIN
                                TEXT
                            END
                        ;

propertyValue           :
                            'Value'
                            BEGIN
                                TEXT
                            END
                        ;

elementRelation         :
                            'Relation'
                            BEGIN
                               RELATION
                            END
                        ;

propertyTypes           :
                            'PropertyTypes'
                            BEGIN
                                newPropertyType+
                            END
                        ;

newPropertyType         :
                            propertyTypeName
                            BEGIN
                                valueOfPropertyType+
                            END
                        ;

propertyTypeName        :
                            TEXT
                        ;

valueOfPropertyType     :
                            BEGIN
                                TEXT
                            END
                        ;

RELATION                :
                            'SINGLE'    |
                            'single'    |
                            'MULTIPLE'  |
                            'multiple'
                        ;
CONNECTION_TYPE         :
                            'DIRECTED'      |
                            'NONDIRECTED'   |
                            'POSITIONAL'
                        ;