'use strict';

angular.module('stepApp')
    .factory('JobPlacementInfo', function ($resource, DateUtils) {
        return $resource('api/jobPlacementInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.workFrom = DateUtils.convertLocaleDateFromServer(data.workFrom);
                    data.workTo = DateUtils.convertLocaleDateFromServer(data.workTo);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.workFrom = DateUtils.convertLocaleDateToServer(data.workFrom);
                    data.workTo = DateUtils.convertLocaleDateToServer(data.workTo);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.workFrom = DateUtils.convertLocaleDateToServer(data.workFrom);
                    data.workTo = DateUtils.convertLocaleDateToServer(data.workTo);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    });
