'use strict';

angular.module('stepApp')
    .factory('RisNewAppForm', function ($resource, DateUtils) {
        return $resource('api/risNewAppForms/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.applicationDate = DateUtils.convertLocaleDateFromServer(data.applicationDate);
                    data.birthDate = DateUtils.convertLocaleDateFromServer(data.birthDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.applicationDate = DateUtils.convertLocaleDateToServer(data.applicationDate);
                    data.birthDate = DateUtils.convertLocaleDateToServer(data.birthDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.applicationDate = DateUtils.convertLocaleDateToServer(data.applicationDate);
                    data.birthDate = DateUtils.convertLocaleDateToServer(data.birthDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    });
