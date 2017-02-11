'use strict';

angular.module('stepApp')
    .factory('JpEmploymentHistorySearch', function ($resource) {
        return $resource('api/_search/jpEmploymentHistorys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('JpEmploymentHistoryJpEmployee', function ($resource) {
        return $resource('api/employmentHistorys/jpEmployee/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('JpEmploymentHistoryFirst', function ($resource) {
        return $resource('api/employmentHistory/experience/jpEmployee/:id', {}, {
            'get': { method: 'GET', isArray: false}
        });
    }).factory('MyFirstEmploymentHistory', function ($resource) {
        return $resource('api/employmentHistory/firstExperience/curr', {}, {
            'get': { method: 'GET', isArray: false}
        });
    });
