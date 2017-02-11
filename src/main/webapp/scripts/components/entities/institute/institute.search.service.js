'use strict';

angular.module('stepApp')
    .factory('InstituteSearch', function ($resource) {
        return $resource('api/_search/institutes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('InstituteByLogin', function ($resource) {
        return $resource('api/institute/current', {}, {
            'query': { method: 'GET', isArray: false}
        });
    }).factory('InstituteByUpazila', function ($resource) {
        return $resource('api/institutes/upazila/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('InstituteByInstLevelName', function ($resource) {
        return $resource('api/institute/instLevel/name/:name', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('GovernmentInstitutes', function ($resource) {
        return $resource('api/institutes/byType/government', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('MpoListedInstitutes', function ($resource) {
        return $resource('api/institute/mpoEnlistedinstitutes', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('InstituteStatusByInstitute', function ($resource) {
        return $resource('api/institute/instituteStatus/:id', {}, {
            'get': { method: 'GET', isArray: false}
        });
    });
