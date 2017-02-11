'use strict';

angular.module('stepApp')
    .factory('RisNewVacancy', function ($resource, DateUtils) {
        return $resource('api/risNewVacancys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.publishDate = DateUtils.convertLocaleDateFromServer(data.publishDate);
                    data.applicationDate = DateUtils.convertLocaleDateFromServer(data.applicationDate);
                    data.createdDate = DateUtils.convertLocaleDateFromServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateFromServer(data.updatedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.publishDate = DateUtils.convertLocaleDateToServer(data.publishDate);
                    data.applicationDate = DateUtils.convertLocaleDateToServer(data.applicationDate);
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.publishDate = DateUtils.convertLocaleDateToServer(data.publishDate);
                    data.applicationDate = DateUtils.convertLocaleDateToServer(data.applicationDate);
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            }
        });
    });
