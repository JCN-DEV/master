'use strict';

/* Controllers */
//google.load('visualization', '1', {
//    packages: ['corechart']
//});
//
//google.setOnLoadCallback(function() {
//    angular.bootstrap(document.body, ['stepApp']);
//});


angular.module('stepApp')
    .controller('CourseInfoController',
        ['$scope', '$state', '$modal', 'DataUtils',
        function ($scope, $state, $modal, DataUtils) {

        $scope.abbreviate = DataUtils.abbreviate;
        $scope.byteSize = DataUtils.byteSize;

        $scope.lineObject = {
            "type": "ComboChart",
            "title": "Line Chart",
            "displayed": false,
            "data": {
                "cols": [
                    {
                        "id": "durationType",
                        "label": "Duration Type",
                        "type": "string",
                        "p": {}
                    },
                    {
                        "id": "semester-id",
                        "label": "Semester",
                        "type": "number",
                        "p": {}
                    },
                    {
                        "id": "month-id",
                        "label": "Month",
                        "type": "number",
                        "p": {}
                    },
                    {
                        "id": "year-id",
                        "label": "Year",
                        "type": "number",
                        "p": {}
                    }
                ],
                "rows": [
                    {
                        "c": [
                            {
                                "v": "Semester"
                            },
                            {
                                "v": 19,
                                "f": "42 items"
                            },
                            {
                                "v": 12,
                                "f": "Ony 12 items"
                            },
                            {
                                "v": 7,
                                "f": "7 servers"
                            }
                        ]
                    },
                    {
                        "c": [
                            {
                                "v": "Month"
                            },
                            {
                                "v": 13
                            },
                            {
                                "v": 1,
                                "f": "1 unit (Out of stock this month)"
                            },
                            {
                                "v": 12
                            }
                        ]
                    },
                    {
                        "c": [
                            {
                                "v": "Year"
                            },
                            {
                                "v": 24
                            },
                            {
                                "v": 5
                            },
                            {
                                "v": 11
                            }
                        ]
                    }
                ]
            },
            "options": {
                "title": "Curriculum Summery",
                "isStacked": "true",
                "fill": 20,
                "displayExactValues": true,
                "vAxis": {
                    "title": "Sales unit",
                    "gridlines": {
                        "count": 10
                    }
                },
                "hAxis": {
                    "title": "Date"
                }
            },
            "formatters": {}
        }

        $scope.pieObject = {
            "type": "PieChart",
            "title": "Pie Chart",
            "displayed": false,
            "data": {
                "cols": [
                    {
                        "id": "month",
                        "label": "Month",
                        "type": "string",
                        "p": {}
                    },
                    {
                        "id": "laptop-id",
                        "label": "Laptop",
                        "type": "number",
                        "p": {}
                    },
                    {
                        "id": "desktop-id",
                        "label": "Desktop",
                        "type": "number",
                        "p": {}
                    },
                    {
                        "id": "server-id",
                        "label": "Server",
                        "type": "number",
                        "p": {}
                    },
                    {
                        "id": "cost-id",
                        "label": "Shipping",
                        "type": "number"
                    }
                ],
                "rows": [
                    {
                        "c": [
                            {
                                "v": "Semester-type"
                            },
                            {
                                "v": 19,
                                "f": "42 items"
                            },
                            {
                                "v": 12,
                                "f": "Ony 12 items"
                            },
                            {
                                "v": 7,
                                "f": "7 servers"
                            },
                            {
                                "v": 4
                            }
                        ]
                    },
                    {
                        "c": [
                            {
                                "v": "Yearly"
                            },
                            {
                                "v": 13
                            },
                            {
                                "v": 1,
                                "f": "1 unit (Out of stock this month)"
                            },
                            {
                                "v": 12
                            },
                            {
                                "v": 2
                            }
                        ]
                    },
                    {
                        "c": [
                            {
                                "v": "Monthly"
                            },
                            {
                                "v": 24
                            },
                            {
                                "v": 5
                            },
                            {
                                "v": 11
                            },
                            {
                                "v": 6
                            }
                        ]
                    }
                ]
            },
            "options": {
                "title": "Curriculum Summery",
                "isStacked": "true",
                "fill": 20,
                "displayExactValues": true,
                "vAxis": {
                    "title": "Curriculum unit",
                    "gridlines": {
                        "count": 10
                    }
                },
                "hAxis": {
                    "title": "Date"
                }
            },
            "formatters": {}
        }
    }]);
