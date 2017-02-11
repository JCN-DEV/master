'use strict';

angular.module('stepApp')
    .factory('InstEmpEduQuali', function ($resource, DateUtils) {
        return $resource('api/instEmpEduQualis/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.resultPublishDate = DateUtils.convertLocaleDateFromServer(data.resultPublishDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.resultPublishDate = DateUtils.convertLocaleDateToServer(data.resultPublishDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.resultPublishDate = DateUtils.convertLocaleDateToServer(data.resultPublishDate);
                    return angular.toJson(data);
                }
            }
        });
    });
