'use strict';

angular.module('stepApp')
    .factory('DteJasperConfiguration', function ($resource, DateUtils) {
        return $resource('api/dteJasperConfigurations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.modifiedDate = DateUtils.convertLocaleDateFromServer(data.modifiedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.modifiedDate = DateUtils.convertLocaleDateToServer(data.modifiedDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.modifiedDate = DateUtils.convertLocaleDateToServer(data.modifiedDate);
                    return angular.toJson(data);
                }
            }
        });
    });
