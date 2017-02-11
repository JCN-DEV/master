'use strict';

angular.module('stepApp')
    .factory('PrlEmpPaymentStopInfo', function ($resource, DateUtils) {
        return $resource('api/prlEmpPaymentStopInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.effectedDateFrom = DateUtils.convertLocaleDateFromServer(data.effectedDateFrom);
                    data.effectedDateTo = DateUtils.convertLocaleDateFromServer(data.effectedDateTo);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.effectedDateFrom = DateUtils.convertLocaleDateToServer(data.effectedDateFrom);
                    data.effectedDateTo = DateUtils.convertLocaleDateToServer(data.effectedDateTo);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.effectedDateFrom = DateUtils.convertLocaleDateToServer(data.effectedDateFrom);
                    data.effectedDateTo = DateUtils.convertLocaleDateToServer(data.effectedDateTo);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    });
