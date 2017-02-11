'use strict';

describe('JpAcademicQualification Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockJpAcademicQualification, MockJpEmployee;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockJpAcademicQualification = jasmine.createSpy('MockJpAcademicQualification');
        MockJpEmployee = jasmine.createSpy('MockJpEmployee');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'JpAcademicQualification': MockJpAcademicQualification,
            'JpEmployee': MockJpEmployee
        };
        createController = function() {
            $injector.get('$controller')("JpAcademicQualificationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:jpAcademicQualificationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
