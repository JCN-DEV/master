'use strict';

angular.module('stepApp')
    .factory('InstEmplWithhelHist', function ($resource, DateUtils) {
        return $resource('api/instEmplWithhelHists/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.startDate = DateUtils.convertLocaleDateFromServer(data.startDate);
                    data.stopDate = DateUtils.convertLocaleDateFromServer(data.stopDate);
                    data.createdDate = DateUtils.convertLocaleDateFromServer(data.createdDate);
                    data.modifiedDate = DateUtils.convertLocaleDateFromServer(data.modifiedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.stopDate = DateUtils.convertLocaleDateToServer(data.stopDate);
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.modifiedDate = DateUtils.convertLocaleDateToServer(data.modifiedDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.stopDate = DateUtils.convertLocaleDateToServer(data.stopDate);
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.modifiedDate = DateUtils.convertLocaleDateToServer(data.modifiedDate);
                    return angular.toJson(data);
                }
            }
        });
    });
