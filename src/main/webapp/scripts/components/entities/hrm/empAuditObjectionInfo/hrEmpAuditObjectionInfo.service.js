'use strict';

angular.module('stepApp')
    .factory('HrEmpAuditObjectionInfo', function ($resource, DateUtils) {
        return $resource('api/hrEmpAuditObjectionInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.replyDate = DateUtils.convertLocaleDateFromServer(data.replyDate);
                    data.jointMeetingDate = DateUtils.convertLocaleDateFromServer(data.jointMeetingDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.replyDate = DateUtils.convertLocaleDateToServer(data.replyDate);
                    data.jointMeetingDate = DateUtils.convertLocaleDateToServer(data.jointMeetingDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.replyDate = DateUtils.convertLocaleDateToServer(data.replyDate);
                    data.jointMeetingDate = DateUtils.convertLocaleDateToServer(data.jointMeetingDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    });
