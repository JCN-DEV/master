'use strict';

angular.module('stepApp')
    .factory('InstFinancialInfoSearch', function ($resource) {
        return $resource('api/_search/instFinancialInfoTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('InstFinancialInfoOne', function ($resource) {
        return $resource('api/instFinancialInfoTemps/one/:id', {}, {
            'get': { method: 'GET', isArray: false}
        });
    }).factory('InstFinancialInfoByInstitute', function ($resource) {
        return $resource('api/instFinancialInfoByInstituteId/:instituteId', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('InstFinancialInfoApprove', function ($resource, DateUtils) {
        return $resource('api/instFinancialInfoTemps/approve', {}, {
            'approve': {
                method: 'PUT',
                isArray: true,
                transformRequest: function (data) {
                   /* data.issueDate = DateUtils.convertLocaleDateToServer(data.issueDate);
                    data.expireDate = DateUtils.convertLocaleDateToServer(data.expireDate);*/
                    return angular.toJson(data);
                }
            }
        });
    });
