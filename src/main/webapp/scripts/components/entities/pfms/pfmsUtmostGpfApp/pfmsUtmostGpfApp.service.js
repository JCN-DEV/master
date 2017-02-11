'use strict';

angular.module('stepApp')
    .factory('PfmsUtmostGpfApp', function ($resource, DateUtils) {
        return $resource('api/pfmsUtmostGpfApps/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.prlDate = DateUtils.convertLocaleDateFromServer(data.prlDate);
                    data.deductionDate = DateUtils.convertLocaleDateFromServer(data.deductionDate);
                    data.billDate = DateUtils.convertLocaleDateFromServer(data.billDate);
                    data.tokenDate = DateUtils.convertLocaleDateFromServer(data.tokenDate);
                    data.applyDate = DateUtils.convertLocaleDateFromServer(data.applyDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.prlDate = DateUtils.convertLocaleDateToServer(data.prlDate);
                    data.deductionDate = DateUtils.convertLocaleDateToServer(data.deductionDate);
                    data.billDate = DateUtils.convertLocaleDateToServer(data.billDate);
                    data.tokenDate = DateUtils.convertLocaleDateToServer(data.tokenDate);
                    data.applyDate = DateUtils.convertLocaleDateToServer(data.applyDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.prlDate = DateUtils.convertLocaleDateToServer(data.prlDate);
                    data.deductionDate = DateUtils.convertLocaleDateToServer(data.deductionDate);
                    data.billDate = DateUtils.convertLocaleDateToServer(data.billDate);
                    data.tokenDate = DateUtils.convertLocaleDateToServer(data.tokenDate);
                    data.applyDate = DateUtils.convertLocaleDateToServer(data.applyDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('PfmsUtmostGpfAppListByEmployee', function ($resource)
    {
        return $resource('api/pfmsUtmostGpfAppListByEmployee/:employeeInfoId', {},
            {
                'get': { method: 'GET', isArray: true}
            });
    });
