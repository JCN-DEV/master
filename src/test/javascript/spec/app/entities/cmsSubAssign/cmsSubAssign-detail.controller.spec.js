'use strict';

describe('CmsSubAssign Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCmsSubAssign, MockCmsCurriculum, MockCmsTrade, MockCmsSemester, MockCmsSyllabus;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCmsSubAssign = jasmine.createSpy('MockCmsSubAssign');
        MockCmsCurriculum = jasmine.createSpy('MockCmsCurriculum');
        MockCmsTrade = jasmine.createSpy('MockCmsTrade');
        MockCmsSemester = jasmine.createSpy('MockCmsSemester');
        MockCmsSyllabus = jasmine.createSpy('MockCmsSyllabus');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'CmsSubAssign': MockCmsSubAssign,
            'CmsCurriculum': MockCmsCurriculum,
            'CmsTrade': MockCmsTrade,
            'CmsSemester': MockCmsSemester,
            'CmsSyllabus': MockCmsSyllabus
        };
        createController = function() {
            $injector.get('$controller')("CmsSubAssignDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:cmsSubAssignUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
