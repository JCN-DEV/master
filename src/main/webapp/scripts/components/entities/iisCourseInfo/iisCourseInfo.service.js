'use strict';

angular.module('stepApp')
    .factory('IisCourseInfo', function ($resource, DateUtils) {
        return $resource('api/iisCourseInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.perDateEdu = DateUtils.convertLocaleDateFromServer(data.perDateEdu);
                    data.perDateBteb = DateUtils.convertLocaleDateFromServer(data.perDateBteb);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.perDateEdu = DateUtils.convertLocaleDateToServer(data.perDateEdu);
                    data.perDateBteb = DateUtils.convertLocaleDateToServer(data.perDateBteb);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.perDateEdu = DateUtils.convertLocaleDateToServer(data.perDateEdu);
                    data.perDateBteb = DateUtils.convertLocaleDateToServer(data.perDateBteb);
                    return angular.toJson(data);
                }
            }
        });
    }) .factory('FindCourseByInstId', function ($resource) {
                  return $resource('api/iisCourseInfoTemps/FindCourseByInstId', {}, {
                      'query': { method: 'GET', isArray: true}
                  })
                });
/*
'use strict';

angular.module('stepApp')
    .factory('IisCourseInfo', function ($resource, DateUtils) {
        return $resource('api/iisCourseInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.perDateEdu = DateUtils.convertLocaleDateFromServer(data.perDateEdu);
                    data.perDateBteb = DateUtils.convertLocaleDateFromServer(data.perDateBteb);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.perDateEdu = DateUtils.convertLocaleDateToServer(data.perDateEdu);
                    data.perDateBteb = DateUtils.convertLocaleDateToServer(data.perDateBteb);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.perDateEdu = DateUtils.convertLocaleDateToServer(data.perDateEdu);
                    data.perDateBteb = DateUtils.convertLocaleDateToServer(data.perDateBteb);
                    return angular.toJson(data);
                }
            }
        });
    }) .factory('FindCourseByInstId', function ($resource) {
                  return $resource('api/iisCourseInfos/FindCourseByInstId', {}, {
                      'query': { method: 'GET', isArray: true}
                  })
                });
*/
