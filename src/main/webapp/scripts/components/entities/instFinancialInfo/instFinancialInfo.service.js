'use strict';

angular.module('stepApp')
    .factory('InstFinancialInfo', function ($resource, DateUtils) {
        return $resource('api/instFinancialInfoTemps/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                isArray: true,
                transformResponse: function (data) {
                    //console.log(data);
                    if(!data){
                        data={id:0};
                    }
                    return data = angular.fromJson(data);
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                   // data.issueDate = DateUtils.convertLocaleDateToServer(data.issueDate);
                   // data.expireDate = DateUtils.convertLocaleDateToServer(data.expireDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.issueDate = DateUtils.convertLocaleDateToServer(data.issueDate);
                    data.expireDate = DateUtils.convertLocaleDateToServer(data.expireDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('InstFinancialInfoDecline', function ($resource) {
        return $resource('api/instFinancialInfoTemps/decline/:id', {}, {
            'update': { method: 'PUT', isArray: true}
        });
    });
