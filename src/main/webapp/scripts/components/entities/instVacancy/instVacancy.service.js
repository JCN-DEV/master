'use strict';

angular.module('stepApp')
    .factory('InstVacancy', function ($resource, DateUtils) {
        return $resource('api/instVacancys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateCreated = DateUtils.convertLocaleDateFromServer(data.dateCreated);
                    data.dateModified = DateUtils.convertLocaleDateFromServer(data.dateModified);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dateCreated = DateUtils.convertLocaleDateToServer(data.dateCreated);
                    data.dateModified = DateUtils.convertLocaleDateToServer(data.dateModified);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dateCreated = DateUtils.convertLocaleDateToServer(data.dateCreated);
                    data.dateModified = DateUtils.convertLocaleDateToServer(data.dateModified);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('InstVacancyGeneralRole', function ($resource, DateUtils) {
        return $resource('api/instVacancys/generalRole/apply', {}, {
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    /*data.dateCreated = DateUtils.convertLocaleDateToServer(data.dateCreated);
                    data.dateModified = DateUtils.convertLocaleDateToServer(data.dateModified);*/
                    return angular.toJson(data);
                }
            }
        });
    });