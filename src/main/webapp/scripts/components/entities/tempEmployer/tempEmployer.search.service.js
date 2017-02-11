'use strict';

angular.module('stepApp')
    .factory('TempEmployerSearch', function ($resource) {
        return $resource('api/_search/tempEmployers/:query', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })
    .factory('TempEmployerByUserId', function ($resource) {
        return $resource('api/tempEmployers/user/:id', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })
    .factory('ApprovedTempEmployer', function ($resource) {
        return $resource('api/tempEmployers/approved', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })
    .factory('PendingTempEmployer', function ($resource) {
        return $resource('api/tempEmployers/pending', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })
    .factory('RejectedTempEmployer', function ($resource) {
        return $resource('api/tempEmployers/rejected', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })
    .factory('TempEmployerWithStatus', function ($resource) {
        return $resource('api/tempEmployersByStatus/:status', {}, {
            'query': {method: 'GET', isArray: true}
        });
    }).factory('ApprovedTempEmployerInstitute', function ($resource) {
        return $resource('api/tempEmployers/approved/institute', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })
    .factory('PendingTempEmployerInstitute', function ($resource) {
        return $resource('api/tempEmployers/pending/institute', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })
    .factory('RejectedTempEmployerInstitute', function ($resource) {
        return $resource('api/tempEmployers/rejected/institute', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })
    .factory('TempEmployerWithInstuteAndStatus', function ($resource) {
        return $resource('api/tempEmployersByInstituteAndStatus/:status', {}, {
            'query': {method: 'GET', isArray: true}
        });
    });
