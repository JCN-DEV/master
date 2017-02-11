'use strict';

angular.module('stepApp')
    .factory('IisCurriculumInfo', function ($resource, DateUtils) {
        return $resource('api/iisCurriculumInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.firstDate = DateUtils.convertLocaleDateFromServer(data.firstDate);
                    data.lastDate = DateUtils.convertLocaleDateFromServer(data.lastDate);
                    data.mpoDate = DateUtils.convertLocaleDateFromServer(data.mpoDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.firstDate = DateUtils.convertLocaleDateToServer(data.firstDate);
                    data.lastDate = DateUtils.convertLocaleDateToServer(data.lastDate);
                    data.mpoDate = DateUtils.convertLocaleDateToServer(data.mpoDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.firstDate = DateUtils.convertLocaleDateToServer(data.firstDate);
                    data.lastDate = DateUtils.convertLocaleDateToServer(data.lastDate);
                    data.mpoDate = DateUtils.convertLocaleDateToServer(data.mpoDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('IisCurriculumInfoMpo', function ($resource, DateUtils) {
        return $resource('api/iisCurriculumInfos/mpoDate/provide', {}, {
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.firstDate = DateUtils.convertLocaleDateToServer(data.firstDate);
                    data.lastDate = DateUtils.convertLocaleDateToServer(data.lastDate);
                    data.mpoDate = DateUtils.convertLocaleDateToServer(data.mpoDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('FindCurriculumByInstId', function ($resource) {
            return $resource('api/iisCurriculumInfos/FindCurriculumByInstId', {}, {
                'query': { method: 'GET', isArray: true}
            })
          });
