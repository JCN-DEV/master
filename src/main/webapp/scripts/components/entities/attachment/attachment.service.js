'use strict';

angular.module('stepApp')
    .factory('Attachment', function ($resource, DateUtils) {
        return $resource('api/attachments/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT',
                 transformRequest: function (data) {
                     data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                     data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                     return angular.toJson(data);
                 }
             },
             'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.date = DateUtils.convertLocaleDateToServer(data.date);
                    return angular.toJson(data);
                }
             }

        });
    }).factory('AttachmentByName', function ($resource, DateUtils) {
        return $resource('api/attachmentByname/:id', {}, {
            'get': {
                method: 'PUT',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
