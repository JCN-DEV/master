'use strict';

angular.module('stepApp')
    .factory('JobPostingInfo', function ($resource, DateUtils) {
        return $resource('api/jobPostingInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.publishedDate = DateUtils.convertLocaleDateFromServer(data.publishedDate);
                    data.applicationDateLine = DateUtils.convertLocaleDateFromServer(data.applicationDateLine);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.publishedDate = DateUtils.convertLocaleDateToServer(data.publishedDate);
                    data.applicationDateLine = DateUtils.convertLocaleDateToServer(data.applicationDateLine);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.publishedDate = DateUtils.convertLocaleDateToServer(data.publishedDate);
                    data.applicationDateLine = DateUtils.convertLocaleDateToServer(data.applicationDateLine);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }

        });
    })
    .factory('getbyCategory', function ($resource) {
        return $resource('api/jobPostingInfos/byCategory', {}, {
            'query': { method: 'GET', isArray: true}

            /*'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.uploadDate = DateUtils.convertLocaleDateFromServer(data.uploadDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            }*/
        });
    })
    .factory('CatByJobs', function ($resource) {
        return $resource('api/jobPostingInfos/byJob/:catId', {}, {
            'query': { method: 'GET', isArray: true}

        });
    });

