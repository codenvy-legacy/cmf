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

grammar Serialization;

import Common;

serialization           :
                            'Elements'  COLON
                            (BEGIN element END ) (','  BEGIN element END )*
                        ;

element                 :
                            elementName

                            elementTemplate
                            elemDesignation
                            conDesignation
                        ;

elementName             :
                            'name' COLON TEXT
                        ;

elementTemplate         :
                            'template' COLON FILENAME
                        ;

elemDesignation         :
                            'elementDesignation' COLON(
                                'INSERTION' |
                                (
                                    'REFERENCE' COLON
                                    BEGIN
                                        elemRefProperty
                                        elemRefTemplate
                                    END
                                )
                            )
                        ;

elemRefProperty         :
                            'property' COLON TEXT
                        ;

elemRefTemplate         :
                            'template' COLON FILENAME
                        ;

conDesignation          :
                            'connectionDesignation' COLON(
                                'ORDERING' |
                                (
                                    'REFERENCE' COLON
                                    BEGIN
                                        conRefProperty
                                        conRefTemplate
                                    END
                                )
                            )
                        ;

conRefProperty          :
                            'property' COLON TEXT
                        ;

conRefTemplate          :
                            'template' COLON FILENAME
                        ;

FILENAME                :   CHAR+'.'CHAR*
                        ;
