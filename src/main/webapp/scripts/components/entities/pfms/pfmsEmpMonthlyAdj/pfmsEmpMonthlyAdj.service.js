'use strict';

angular.module('stepApp')
    .factory('PfmsEmpMonthlyAdj', function ($resource, DateUtils) {
        return $resource('api/pfmsEmpMonthlyAdjs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('PfmsEmpMonthlyAdjListByEmployee', function ($resource)
    {
        return $resource('api/pfmsEmpMonthlyAdjListByEmployee/:employeeInfoId', {},
            {
                'get': { method: 'GET', isArray: true}
            });
    })
    //.factory('PfmsEmpMonthlyAdjMaxDate', function ($resource)
    //{
    //    return $resource('api/pfmsEmpMonthlyAdjMaxDateObj/:orgtype/:desigId/:refid', {},
    //        {
    //            'get': { method: 'GET', isArray: true}
    //        });
    //
// })
;
