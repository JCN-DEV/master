'use strict';

describe('CmsSubject Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCmsSubject, MockCmsCurriculum, MockCmsSyllabus;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCmsSubject = jasmine.createSpy('MockCmsSubject');
        MockCmsCurriculum = jasmine.createSpy('MockCmsCurriculum');
        MockCmsSyllabus = jasmine.createSpy('MockCmsSyllabus');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'CmsSubject': MockCmsSubject,
            'CmsCurriculum': MockCmsCurriculum,
            'CmsSyllabus': MockCmsSyllabus
        };
        createController = function() {
            $injector.get('$controller')("CmsSubjectDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:cmsSubjectUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
