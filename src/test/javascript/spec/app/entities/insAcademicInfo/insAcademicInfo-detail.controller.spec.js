'use strict';

describe('InsAcademicInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInsAcademicInfo, MockInstitute;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInsAcademicInfo = jasmine.createSpy('MockInsAcademicInfo');
        MockInstitute = jasmine.createSpy('MockInstitute');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InsAcademicInfo': MockInsAcademicInfo,
            'Institute': MockInstitute
        };
        createController = function() {
            $injector.get('$controller')("InsAcademicInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:insAcademicInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
