'use strict';

angular.module('stepApp')
    .factory('IisCurriculumInfoTemp', function ($resource, DateUtils) {
        return $resource('api/iisCurriculumInfoTemps/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.firstDate = DateUtils.convertLocaleDateFromServer(data.firstDate);
                    data.lastDate = DateUtils.convertLocaleDateFromServer(data.lastDate);
                    data.mpoDate = DateUtils.convertLocaleDateFromServer(data.mpoDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.firstDate = DateUtils.convertLocaleDateToServer(data.firstDate);
                    data.lastDate = DateUtils.convertLocaleDateToServer(data.lastDate);
                    data.mpoDate = DateUtils.convertLocaleDateToServer(data.mpoDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.firstDate = DateUtils.convertLocaleDateToServer(data.firstDate);
                    data.lastDate = DateUtils.convertLocaleDateToServer(data.lastDate);
                    data.mpoDate = DateUtils.convertLocaleDateToServer(data.mpoDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    });
