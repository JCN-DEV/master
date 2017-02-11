'use strict';

angular.module('stepApp')
    .factory('SmsServiceReply', function ($resource, DateUtils) {
        return $resource('api/smsServiceReplys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.replyDate = DateUtils.convertLocaleDateFromServer(data.replyDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.replyDate = DateUtils.convertLocaleDateToServer(data.replyDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.replyDate = DateUtils.convertLocaleDateToServer(data.replyDate);
                    return angular.toJson(data);
                }
            }
        });
    });
