'use strict';

angular.module('stepApp')
    .factory('DlFileType', function ($resource, DateUtils) {
        return $resource('api/dlFileTypes/:id', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createdDate = DateUtils.convertLocaleDateFromServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateFromServer(data.updatedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            }
        });
    })

    .factory('DlFileTypeByfileType', function ($resource) {
        return $resource('api/dlFileTypes/dlFileTypeUnique/:fileType', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })

    .factory('FindActiveFileType', function ($resource) {
        return $resource('api/dlFileType/allFileType', {}, {
            'query': {method: 'GET', isArray: true}
        });
    });
