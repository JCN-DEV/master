'use strict';

angular.module('stepApp')
    .factory('IisCourseInfoTemp', function ($resource, DateUtils) {
        return $resource('api/iisCourseInfoTemps/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.perDateEdu = DateUtils.convertLocaleDateFromServer(data.perDateEdu);
                    data.perDateBteb = DateUtils.convertLocaleDateFromServer(data.perDateBteb);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.perDateEdu = DateUtils.convertLocaleDateToServer(data.perDateEdu);
                    data.perDateBteb = DateUtils.convertLocaleDateToServer(data.perDateBteb);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.perDateEdu = DateUtils.convertLocaleDateToServer(data.perDateEdu);
                    data.perDateBteb = DateUtils.convertLocaleDateToServer(data.perDateBteb);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    });
